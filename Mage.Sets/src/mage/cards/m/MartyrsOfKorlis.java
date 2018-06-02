
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author MarcoMarin
 */
public final class MartyrsOfKorlis extends CardImpl {

    public MartyrsOfKorlis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // As long as Martyrs of Korlis is untapped, all damage that would be dealt to you by artifacts is dealt to Martyrs of Korlis instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new RedirectArtifactDamageFromPlayerToSourceEffect(Duration.WhileOnBattlefield),
                new InvertCondition(SourceTappedCondition.instance),
                "{this} redirects artifact damage from controller as long as it's untapped")));
    }

    public MartyrsOfKorlis(final MartyrsOfKorlis card) {
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
        if (event.getTargetId().equals(source.getControllerId())&&
                game.getPermanentOrLKIBattlefield(event.getSourceId()).isArtifact()) {
            this.redirectTarget.updateTarget(source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
