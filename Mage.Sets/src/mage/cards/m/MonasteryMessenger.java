package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonasteryMessenger extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("noncreature, nonland card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public MonasteryMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2/U}{2/R}{2/W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, put up to one target noncreature, nonland card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private MonasteryMessenger(final MonasteryMessenger card) {
        super(card);
    }

    @Override
    public MonasteryMessenger copy() {
        return new MonasteryMessenger(this);
    }
}
