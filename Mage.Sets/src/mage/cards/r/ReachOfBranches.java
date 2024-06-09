package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TreefolkShamanToken;

/**
 *
 * @author LevelX2
 */
public final class ReachOfBranches extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "a Forest");

    public ReachOfBranches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        // Create a 2/5 green Treefolk Shaman creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreefolkShamanToken()));

        // Whenever a Forest enters the battlefield under your control, you may return Reach of Branches from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(), filter, true));
    }

    private ReachOfBranches(final ReachOfBranches card) {
        super(card);
    }

    @Override
    public ReachOfBranches copy() {
        return new ReachOfBranches(this);
    }
}
