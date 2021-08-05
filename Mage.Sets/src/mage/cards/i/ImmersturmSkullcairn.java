package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImmersturmSkullcairn extends CardImpl {

    public ImmersturmSkullcairn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Immersturm Skullcairn enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {1}{B}{R}{R}, {T}, Sacrifice Immersturm Skullcairn: It deals 3 damage to target player. That player discards a card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(3, "it"),
                new ManaCostsImpl<>("{1}{B}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DiscardTargetEffect(1).setText("That player discards a card"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ImmersturmSkullcairn(final ImmersturmSkullcairn card) {
        super(card);
    }

    @Override
    public ImmersturmSkullcairn copy() {
        return new ImmersturmSkullcairn(this);
    }
}
