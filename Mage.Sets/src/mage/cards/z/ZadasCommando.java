package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class ZadasCommando extends CardImpl {

    public ZadasCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.ARCHER, SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Zada's Commando deals 1 damage to target opponent.
        Ability ability = new CohortAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private ZadasCommando(final ZadasCommando card) {
        super(card);
    }

    @Override
    public ZadasCommando copy() {
        return new ZadasCommando(this);
    }
}
