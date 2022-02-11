
package mage.cards.e;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.command.emblems.ElspethSunsChampionEmblem;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class ElspethSunsChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public ElspethSunsChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);

        this.setStartingLoyalty(4);

        // +1: Create three 1/1 white Soldier creature tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierToken(), 3), 1));
        // -3: Destroy all creatures with power 4 or greater.
        this.addAbility(new LoyaltyAbility(new DestroyAllEffect(filter), -3));
        // -7: You get an emblem with "Creatures you control get +2/+2 and have flying."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ElspethSunsChampionEmblem()), -7));
    }

    private ElspethSunsChampion(final ElspethSunsChampion card) {
        super(card);
    }

    @Override
    public ElspethSunsChampion copy() {
        return new ElspethSunsChampion(this);
    }
}
