package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LocthwainGargoyle extends CardImpl {

    public LocthwainGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {4}: Locthwain Gargoyle gets +2/+0 and gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn).setText("{this} gets +2/+0"
        ), new GenericManaCost(4));
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.addAbility(ability);
    }

    private LocthwainGargoyle(final LocthwainGargoyle card) {
        super(card);
    }

    @Override
    public LocthwainGargoyle copy() {
        return new LocthwainGargoyle(this);
    }
}
