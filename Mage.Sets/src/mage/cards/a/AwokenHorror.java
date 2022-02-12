package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AwokenHorror extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Horror creatures");

    static {
        filter.add(Predicates.not(SubType.HORROR.getPredicate()));
    }

    public AwokenHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.KRAKEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);
        this.color.setBlue(true);

        this.nightCard = true;

        // When this creature transforms into Awoken Horrow, return all non-Horror creatures to their owners' hands.
        this.addAbility(new TransformIntoSourceTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)));
    }

    private AwokenHorror(final AwokenHorror card) {
        super(card);
    }

    @Override
    public AwokenHorror copy() {
        return new AwokenHorror(this);
    }
}
