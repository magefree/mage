package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SkeletonMenaceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GutTrueSoulZealot extends CardImpl {

    public GutTrueSoulZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, you may sacrifice another creature or an artifact. If you do, create a 4/1 black Skeleton creature token with menace that's tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(
                        new SkeletonMenaceToken(), 1, true, true
                ), new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_OTHER_CREATURE)
        ), 1));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private GutTrueSoulZealot(final GutTrueSoulZealot card) {
        super(card);
    }

    @Override
    public GutTrueSoulZealot copy() {
        return new GutTrueSoulZealot(this);
    }
}
