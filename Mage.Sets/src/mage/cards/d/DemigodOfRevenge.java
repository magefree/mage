package mage.cards.d;

import mage.MageInt;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DemigodOfRevenge extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Demigod of Revenge");

    static {
        filter.add(new NamePredicate("Demigod of Revenge"));
    }

    public DemigodOfRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/R}{B/R}{B/R}{B/R}{B/R}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When you cast Demigod of Revenge, return all cards named Demigod of Revenge from your graveyard to the battlefield.
        this.addAbility(new CastSourceTriggeredAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter)));
    }

    private DemigodOfRevenge(final DemigodOfRevenge card) {
        super(card);
    }

    @Override
    public DemigodOfRevenge copy() {
        return new DemigodOfRevenge(this);
    }
}
