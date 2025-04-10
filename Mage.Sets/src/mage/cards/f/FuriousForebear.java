package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class FuriousForebear extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public FuriousForebear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever a creature you control dies while this card is in your graveyard, you may pay {1}{W}. If you do, return this card from your graveyard to your hand.
        Effect effect = new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{1}{W}"));
        Ability ability = new DiesCreatureTriggeredAbility(Zone.GRAVEYARD, effect,false, filter, false)
                .setTriggerPhrase("Whenever a creature you control dies while this card is in your graveyard, ");
        this.addAbility(ability);
    }

    private FuriousForebear(final FuriousForebear card) {
        super(card);
    }

    @Override
    public FuriousForebear copy() {
        return new FuriousForebear(this);
    }
}
