package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenOfTheForge extends CardImpl {

    public OmenOfTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Omen of the Forge enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(2, "it")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {2}{R}, Sacrifice Omen of the Forge: Scry 2.
        ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OmenOfTheForge(final OmenOfTheForge card) {
        super(card);
    }

    @Override
    public OmenOfTheForge copy() {
        return new OmenOfTheForge(this);
    }
}
