package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.effects.common.DevourEffect.DevourFactor;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.util.SubTypeList;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VoraciousDragon extends CardImpl {

    public VoraciousDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(DevourFactor.Devour1));

        // When Voracious Dragon enters the battlefield, it deals damage to any target equal to twice the number of Goblins it devoured.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new TwiceDevouredGoblins(), "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public VoraciousDragon(final VoraciousDragon card) {
        super(card);
    }

    @Override
    public VoraciousDragon copy() {
        return new VoraciousDragon(this);
    }
}

class TwiceDevouredGoblins implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            for (Ability ability : sourcePermanent.getAbilities()) {
                if (ability instanceof DevourAbility) {
                    for (Effect abilityEffect : ability.getEffects()) {
                        if (abilityEffect instanceof DevourEffect) {
                            DevourEffect devourEffect = (DevourEffect) abilityEffect;
                            int amountGoblins = 0;
                            for (SubTypeList subtypesItem : devourEffect.getSubtypes(game, sourcePermanent.getId())) {
                                if (subtypesItem.contains(SubType.GOBLIN)) {
                                    ++amountGoblins;
                                }
                            }
                            return amountGoblins * 2;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public TwiceDevouredGoblins copy() {
        return new TwiceDevouredGoblins();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "twice the number of Goblins it devoured";
    }
}
