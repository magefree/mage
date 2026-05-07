package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SunderingArchaic extends CardImpl {

    private static FilterPermanent filter = new FilterNonlandPermanent(
            "nonland permanent an opponent controls with mana value less "
                    + "than or equal to the number of colors of mana spent to cast this creature");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(SunderingArchaicPredicate.instance);
    }

    public SunderingArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Converge -- When this creature enters, exile target nonland permanent an opponent controls with mana value less than or equal to the number of colors of mana spent to cast this creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.setAbilityWord(AbilityWord.CONVERGE);
        this.addAbility(ability);

        // {2}: Put target card from a graveyard on the bottom of its owner's library.
        ability = new SimpleActivatedAbility(
                new PutOnLibraryTargetEffect(false),
                new GenericManaCost(2)
        );
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private SunderingArchaic(final SunderingArchaic card) {
        super(card);
    }

    @Override
    public SunderingArchaic copy() {
        return new SunderingArchaic(this);
    }
}

enum SunderingArchaicPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent inputObject = input.getObject();
        if (inputObject == null) {
            return false;
        }
        int convergeValue =
                ColorsOfManaSpentToCastCount.getInstance()
                        .calculate(game, input.getSource(), null);
        return convergeValue >= input.getObject().getManaValue();
    }
}
