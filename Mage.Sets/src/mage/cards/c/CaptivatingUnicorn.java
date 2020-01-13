package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptivatingUnicorn extends CardImpl {

    public CaptivatingUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Constellation — Whenever an enchantment enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new ConstellationAbility(new TapTargetEffect(), false, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private CaptivatingUnicorn(final CaptivatingUnicorn card) {
        super(card);
    }

    @Override
    public CaptivatingUnicorn copy() {
        return new CaptivatingUnicorn(this);
    }
}
