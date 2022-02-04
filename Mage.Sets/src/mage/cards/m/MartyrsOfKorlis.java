
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class MartyrsOfKorlis extends CardImpl {

    public MartyrsOfKorlis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // As long as Martyrs of Korlis is untapped, all damage that would be dealt to you by artifacts is dealt to Martyrs of Korlis instead.
        Effect effect = new ConditionalReplacementEffect(
                new RedirectArtifactDamageFromPlayerToSourceEffect(Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED,
                null);
        effect.setText("{this} redirects artifact damage from controller as long as it's untapped");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MartyrsOfKorlis(final MartyrsOfKorlis card) {
        super(card);
    }

    @Override
    public MartyrsOfKorlis copy() {
        return new MartyrsOfKorlis(this);
    }
}

class RedirectArtifactDamageFromPlayerToSourceEffect extends RedirectionEffect {

    public RedirectArtifactDamageFromPlayerToSourceEffect(Duration duration) {
        super(duration);
    }

    public RedirectArtifactDamageFromPlayerToSourceEffect(final RedirectArtifactDamageFromPlayerToSourceEffect effect) {
        super(effect);
    }

    @Override
    public RedirectArtifactDamageFromPlayerToSourceEffect copy() {
        return new RedirectArtifactDamageFromPlayerToSourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId()) &&
                game.getPermanentOrLKIBattlefield(event.getSourceId()).isArtifact(game)) {

            TargetPermanent target = new TargetPermanent();
            target.add(source.getSourceId(), game);
            this.redirectTarget = target;

            return true;
        }
        return false;
    }
}
