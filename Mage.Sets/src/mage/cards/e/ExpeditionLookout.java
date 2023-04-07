package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpeditionLookout extends CardImpl {

    public ExpeditionLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as an opponent has eight or more cards in their graveyard, Expedition Lookout can attack as though it didn't have defender and it can't be blocked.
        Ability ability = new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT
        ).setText("as long as an opponent has eight or more cards in their graveyard, " +
                "{this} can attack as though it didn't have defender"));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), CardsInOpponentGraveyardCondition.EIGHT, "and can't be blocked"
        ));
        this.addAbility(ability.addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private ExpeditionLookout(final ExpeditionLookout card) {
        super(card);
    }

    @Override
    public ExpeditionLookout copy() {
        return new ExpeditionLookout(this);
    }
}
