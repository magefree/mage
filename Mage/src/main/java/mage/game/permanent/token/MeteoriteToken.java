package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author Susucr
 */
public final class MeteoriteToken extends TokenImpl {

    public MeteoriteToken() {
        super("Meteorite", "colorless artifact token named Meteorite with "
                + "\"When Meteorite enters the battlefield, it deals 2 damage to any target\" "
                + "and \"{T}: Add one mana of any color.\"");
        cardType.add(CardType.ARTIFACT);

        // When Meteorite enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private MeteoriteToken(final MeteoriteToken token) {
        super(token);
    }

    @Override
    public MeteoriteToken copy() {
        return new MeteoriteToken(this);
    }
}
