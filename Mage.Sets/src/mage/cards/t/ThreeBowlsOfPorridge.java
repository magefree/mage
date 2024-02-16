package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThreeBowlsOfPorridge extends CardImpl {

    public ThreeBowlsOfPorridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.FOOD);

        // {2}, {T}: Choose one that hasn't been chosen --
        // * Three Bowls of Porridge deals 2 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.setModeTag("deals damage");
        ability.getModes().setLimitUsageByOnce(false);

        // * Tap target creature.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        mode.setModeTag("tap");
        ability.addMode(mode);

        // * Sacrifice Three Bowls of Porridge. You gain 3 life.
        mode = new Mode(new SacrificeSourceEffect());
        mode.addEffect(new GainLifeEffect(3));
        mode.setModeTag("sacrifice and gain life");
        ability.addMode(mode);
        ability.addHint(ModesAlreadyUsedHint.instance);

        this.addAbility(ability);
    }

    private ThreeBowlsOfPorridge(final ThreeBowlsOfPorridge card) {
        super(card);
    }

    @Override
    public ThreeBowlsOfPorridge copy() {
        return new ThreeBowlsOfPorridge(this);
    }
}
