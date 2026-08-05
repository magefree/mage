package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GandalfWanderingWizard extends CardImpl {

    public GandalfWanderingWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // {6}: Gandalf's owner shuffles him into their library and draws three cards.
        Ability ability = new SimpleActivatedAbility(
            new ShuffleIntoLibrarySourceEffect().setText("{this}'s owner shuffles him into their library"),
            new ManaCostsImpl<>("{6}")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).setText("and draws three cards"));
        this.addAbility(ability);
    }

    private GandalfWanderingWizard(final GandalfWanderingWizard card) {
        super(card);
    }

    @Override
    public GandalfWanderingWizard copy() {
        return new GandalfWanderingWizard(this);
    }
}
