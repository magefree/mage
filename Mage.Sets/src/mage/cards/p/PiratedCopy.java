package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiratedCopy extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("this creature or another creature with the same name");

    static {
        filter.add(PiratedCopyPredicate.instance);
    }

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.addSubType(SubType.PIRATE);
            blueprint.getAbilities().add(new DealsDamageToAPlayerAllTriggeredAbility(
                    new DrawCardSourceControllerEffect(1), filter,
                    true, SetTargetPointer.NONE, true
            ));
            return true;
        }

        @Override
        public String getText() {
            return ", except it's a Pirate in addition to its other types and it has \"Whenever this creature " +
                    "or another creature with the same name deals combat damage to a player, you may draw a card.\"";
        }
    };

    public PiratedCopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Pirated Copy enter the battlefield as a copy of any creature on the battlefield, except it's a Pirate in addition to its other types and it has "Whenever this creature or another creature with the same name deals combat damage to a player, you may draw a card."
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(applier), true));
    }

    private PiratedCopy(final PiratedCopy card) {
        super(card);
    }

    @Override
    public PiratedCopy copy() {
        return new PiratedCopy(this);
    }
}

enum PiratedCopyPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return !AnotherPredicate.instance.apply(input, game)
                || (input.getObject().isCreature(game) && CardUtil.haveSameNames(input.getObject(), input.getSource().getSourcePermanentOrLKI(game)));
    }
}