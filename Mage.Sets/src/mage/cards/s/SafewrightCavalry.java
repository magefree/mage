package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SafewrightCavalry extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF);

    public SafewrightCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This creature can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));

        // {5}: Target Elf you control gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(2, 2), new GenericManaCost(5));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SafewrightCavalry(final SafewrightCavalry card) {
        super(card);
    }

    @Override
    public SafewrightCavalry copy() {
        return new SafewrightCavalry(this);
    }
}
