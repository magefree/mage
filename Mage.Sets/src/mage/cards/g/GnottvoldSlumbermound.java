package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TrollWarriorToken;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnottvoldSlumbermound extends CardImpl {

    public GnottvoldSlumbermound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Gnottvold Slumbermound enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {3}{R}{G}{G}, {T}, Sacrifice Gnottvold Slumbermound: Destroy target land. Create a 4/4 green Troll Warrior creature token with trample.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect().setText("destroy target land."), new ManaCostsImpl<>("{3}{R}{G}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateTokenEffect(new TrollWarriorToken()));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GnottvoldSlumbermound(final GnottvoldSlumbermound card) {
        super(card);
    }

    @Override
    public GnottvoldSlumbermound copy() {
        return new GnottvoldSlumbermound(this);
    }
}
