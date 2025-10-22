(() => {
    const baseUrl = "sounds";
    const CLICK_DURATION_MS = 300;

    // Inicialización del audio de clic
    if (!window._clickOption) {
        window._clickOption = new Audio(`${baseUrl}/click_option.wav`);
        window._clickOption.preload = "auto";
    }

    // Función para reanudar el avance
    function resumeAction(tag, targetUrl) {
        if (tag === 'A' && targetUrl) {
            window.location.href = targetUrl;
        }

        else if (tag === 'BUTTON') {
             const button = document.querySelector('button[data-sfx-pending="true"]');
             if (button) {
                 button.removeAttribute('data-sfx-pending');
                 button.click();
             }
        }
    }

    // Función para iniciar la música del padre (main.html)
    function startParentMusic() {
        try {
            const parentBgMusic = window.parent.document.getElementById('bg-music');
            if (parentBgMusic && parentBgMusic.paused) {
                parentBgMusic.play().catch(err => {});
            }
        } catch (e) {
        } // Manejar el error de acceso al padre
    }

    // Manejador de eventos: Intercepta, reproduce SFX y retrasa la acción
    document.addEventListener("mousedown", (e) => {
        const el = e.target.closest("button, a");

        // Verifica que sea una acción de navegación (enlace o botón de formulario)
        const isGameAction = el && (el.form || (el.href && el.href !== '#'));

        if (isGameAction && !el.disabled && !el.getAttribute('data-sfx-pending')) {

            startParentMusic();

            e.preventDefault();
            el.setAttribute('data-sfx-pending', 'true');

            const audio = window._clickOption;
            audio.currentTime = 0;

            // Captura las variables antes de entrar al setTimeout
            const tag = el.tagName;
            const targetUrl = el.href;

            // Reproduce el SFX
            audio.play().catch(() => {
                resumeAction(tag, targetUrl); // Si falla, avanza de inmediato
            });

            // Retrasa la acción para que el sonido se escuche
            setTimeout(() => {
                resumeAction(tag, targetUrl);
            }, CLICK_DURATION_MS);
        }
    });

    // Evita que el evento 'click' nativo se dispare antes de tiempo
    document.addEventListener("click", (e) => {
        const el = e.target.closest("button, a");
        if (el && el.getAttribute('data-sfx-pending')) {
            e.preventDefault();
            e.stopImmediatePropagation();
        }
    }, true);
})();