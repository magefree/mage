package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class JornGodOfWinter extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent();
    private static final FilterPermanentCard filter2 = new FilterPermanentCard("snow permanent card from your graveyard");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
    }

    public JornGodOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY, SuperType.SNOW}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{G}",
                "Kaldring, the Rimestaff",
                new SuperType[]{SuperType.LEGENDARY, SuperType.SNOW}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{U}{B}"
        );

        // 1.
        // Jorn, God of Winter
        // Legendary Snow Creature - God
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(3));

        // Whenever Jorn attacks, untap each snow permanent you control.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new UntapAllControllerEffect(
                filter, "untap each snow permanent you control"), false
        ));

        // 2.
        // Kaldring, the Rimestaff
        // Legendary Snow Artifact
        // {T}: You may play target snow permanent card from your graveyard this turn. If you do, it enters the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(new KaldringTheRimestaffEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.getRightHalfCard().addAbility(ability);
    }

    private JornGodOfWinter(final JornGodOfWinter card) {
        super(card);
    }

    @Override
    public JornGodOfWinter copy() {
        return new JornGodOfWinter(this);
    }
}

class KaldringTheRimestaffEffect extends OneShotEffect {

    public KaldringTheRimestaffEffect() {
        super(Outcome.Benefit);
        staticText = "You may play target snow permanent card from your graveyard this turn. If you do, it enters the battlefield tapped";
    }

    private KaldringTheRimestaffEffect(final KaldringTheRimestaffEffect effect) {
        super(effect);
    }

    @Override
    public KaldringTheRimestaffEffect copy() {
        return new KaldringTheRimestaffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, null);
            ContinuousEffect effect = new KaldringTheRimestaffTapEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class KaldringTheRimestaffTapEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    public KaldringTheRimestaffTapEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Tap);
        this.cardId = cardId;
    }

    private KaldringTheRimestaffTapEffect(final KaldringTheRimestaffTapEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public KaldringTheRimestaffTapEffect copy() {
        return new KaldringTheRimestaffTapEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(cardId);
    }
}
