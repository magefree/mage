
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author LevelX2
 */
public final class SlyRequisitioner extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("a nontoken artifact you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public SlyRequisitioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Whenever a nontoken artifact you control is put into a graveyard from the battlefield, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new CreateTokenEffect(new ServoToken()), false, filter, false));
    }

    private SlyRequisitioner(final SlyRequisitioner card) {
        super(card);
    }

    @Override
    public SlyRequisitioner copy() {
        return new SlyRequisitioner(this);
    }
}
