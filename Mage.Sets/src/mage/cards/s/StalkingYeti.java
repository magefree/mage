
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class StalkingYeti extends CardImpl {

    public StalkingYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stalking Yeti enters the battlefield, if it's on the battlefield, it deals damage equal to its power to target creature an opponent controls and that creature deals damage equal to its power to Stalking Yeti.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new StalkingYetiEffect()),
                SourceOnBattlefieldCondition.instance,
                "When {this} enters the battlefield, if it's on the battlefield, "
                + "it deals damage equal to its power to target creature an opponent controls "
                + "and that creature deals damage equal to its power to {this}."
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {2}{snow}: Return Stalking Yeti to its owner's hand. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{2}{S}")));
    }

    private StalkingYeti(final StalkingYeti card) {
        super(card);
    }

    @Override
    public StalkingYeti copy() {
        return new StalkingYeti(this);
    }
}

class StalkingYetiEffect extends OneShotEffect {

    StalkingYetiEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals damage equal to its power to target creature an opponent controls "
                + "and that creature deals damage equal to its power to {this}";
    }

    StalkingYetiEffect(final StalkingYetiEffect effect) {
        super(effect);
    }

    @Override
    public StalkingYetiEffect copy() {
        return new StalkingYetiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = game.getPermanent(source.getSourceId());
        Permanent thatCreature = game.getPermanent(source.getFirstTarget());
        if (thisCreature == null || thatCreature == null) {
            return false;
        }
        thatCreature.damage(thisCreature.getPower().getValue(), thisCreature.getId(), source, game, false, true);
        thisCreature.damage(thatCreature.getPower().getValue(), thatCreature.getId(), source, game, false, true);
        return true;
    }
}
