package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TitaniaProtectorOfArgothElementalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitaniaNaturesForce extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forests");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.FOREST, "a Forest");
    private static final FilterPermanent filter3
            = new FilterControlledPermanent(SubType.ELEMENTAL, "an Elemental you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public TitaniaNaturesForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may play Forests from your graveyard.
        this.addAbility(new SimpleStaticAbility(new PlayLandsFromGraveyardControllerEffect(filter)));

        // Whenever a Forest enters the battlefield under your control, create a 5/3 green Elemental creature token.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CreateTokenEffect(new TitaniaProtectorOfArgothElementalToken()), filter2
        ));

        // Whenever an Elemental you control dies, you may mill three cards.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new MillCardsControllerEffect(3), true, filter3
        ));
    }

    private TitaniaNaturesForce(final TitaniaNaturesForce card) {
        super(card);
    }

    @Override
    public TitaniaNaturesForce copy() {
        return new TitaniaNaturesForce(this);
    }
}
