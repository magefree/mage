package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GolemXXToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskOfTheJadecrafter extends CardImpl {

    public MaskOfTheJadecrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {X}, {T}, Sacrifice Mask of the Jadecrafter: Create an X/X colorless Golem artifact creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new MaskOfTheJadecrafterEffect(), new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Unearth {2}{G}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{G}")));
    }

    private MaskOfTheJadecrafter(final MaskOfTheJadecrafter card) {
        super(card);
    }

    @Override
    public MaskOfTheJadecrafter copy() {
        return new MaskOfTheJadecrafter(this);
    }
}

class MaskOfTheJadecrafterEffect extends OneShotEffect {

    MaskOfTheJadecrafterEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X colorless Golem artifact creature token";
    }

    private MaskOfTheJadecrafterEffect(final MaskOfTheJadecrafterEffect effect) {
        super(effect);
    }

    @Override
    public MaskOfTheJadecrafterEffect copy() {
        return new MaskOfTheJadecrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new GolemXXToken(source.getManaCostsToPay().getX()).putOntoBattlefield(1, game, source);
    }
}
