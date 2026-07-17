package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ChromeDome extends CardImpl {

    public ChromeDome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Other artifact creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        )));

        // {5}: Create a token that's a copy of another target artifact you control. That token gains haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(
                new ChromeDomeEffect(), new GenericManaCost(5));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT));
        this.addAbility(ability);
    }

    private ChromeDome(final ChromeDome card) {
        super(card);
    }

    @Override
    public ChromeDome copy() {
        return new ChromeDome(this);
    }
}

class ChromeDomeEffect extends OneShotEffect {

    ChromeDomeEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target artifact you control. " +
            "That token gains haste. Sacrifice it at the beginning of the next end step";
    }

    private ChromeDomeEffect(final ChromeDomeEffect effect) {
        super(effect);
    }

    @Override
    public ChromeDomeEffect copy() {
        return new ChromeDomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect().setHasHaste(true);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
