package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrubStoriedMatriarch extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard(SubType.GOBLIN);

    public GrubStoriedMatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOBLIN, SubType.WARLOCK}, "{2}{B}",
                "Grub, Notorious Auntie",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOBLIN, SubType.WARRIOR}, "R"
        );
        this.getLeftHalfCard().setPT(2, 1);
        this.getRightHalfCard().setPT(2, 1);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Whenever this creature enters or transforms into Grub, Storied Matriarch, return up to one target Goblin card from your graveyard to your hand.
        Ability ability = new TransformsOrEntersTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your first main phase, you may pay {R}. If you do, transform Grub.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{R}"))
        ));

        // Grub, Notorious Auntie
        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Whenever Grub attacks, you may blight 1. If you do, create a tapped and attacking token that's a copy of the blighted creature, except it has "At the beginning of the end step, sacrifice this token."
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new GrubStoriedMatriarchEffect()));

        // At the beginning of your first main phase, you may pay {B}. If you do, transform Grub.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{B}"))
        ));
    }

    private GrubStoriedMatriarch(final GrubStoriedMatriarch card) {
        super(card);
    }

    @Override
    public GrubStoriedMatriarch copy() {
        return new GrubStoriedMatriarch(this);
    }
}

class GrubStoriedMatriarchEffect extends OneShotEffect {

    GrubStoriedMatriarchEffect() {
        super(Outcome.Benefit);
        staticText = "you may blight 1. If you do, create a tapped and attacking token that's a copy of " +
                "the blighted creature, except it has \"At the beginning of the end step, sacrifice this token.\"";
    }

    private GrubStoriedMatriarchEffect(final GrubStoriedMatriarchEffect effect) {
        super(effect);
    }

    @Override
    public GrubStoriedMatriarchEffect copy() {
        return new GrubStoriedMatriarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || !BlightCost.canBlight(player.getId(), game, source)
                || !player.chooseUse(outcome, "Blight 1?", source, game)) {
            return false;
        }
        Permanent permanent = BlightCost.doBlight(player, 1, game, source);
        return permanent != null
                && new CreateTokenCopyTargetEffect(null, null, false, 1, true, true)
                .setSavedPermanent(permanent)
                .addAdditionalAbilities(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect()))
                .apply(game, source);
    }
}
