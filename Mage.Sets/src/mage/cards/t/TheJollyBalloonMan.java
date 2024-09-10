package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheJollyBalloonMan extends CardImpl {

    public TheJollyBalloonMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLOWN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}, {T}: Create a token that's a copy of another target creature you control, except it's a 1/1 red Balloon creature in addition to its other colors and types and it has flying and haste. Sacrifice it at the beginning of the next end step. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new TheJollyBalloonManEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private TheJollyBalloonMan(final TheJollyBalloonMan card) {
        super(card);
    }

    @Override
    public TheJollyBalloonMan copy() {
        return new TheJollyBalloonMan(this);
    }
}

class TheJollyBalloonManEffect extends OneShotEffect {

    TheJollyBalloonManEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target creature you control, " +
                "except it's a 1/1 red Balloon creature in addition to its other colors " +
                "and types and it has flying and haste. Sacrifice it at the beginning of the next end step";
    }

    private TheJollyBalloonManEffect(final TheJollyBalloonManEffect effect) {
        super(effect);
    }

    @Override
    public TheJollyBalloonManEffect copy() {
        return new TheJollyBalloonManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, true, 1, false,
                false, null, 1, 1, true
        );
        effect.setExtraColor(ObjectColor.RED);
        effect.withAdditionalSubType(SubType.BALLOON);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
