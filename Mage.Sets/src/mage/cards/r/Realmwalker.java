package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class Realmwalker extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("cast creature spells of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public Realmwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // As Realmwalker enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells of the chosen type from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));
    }

    private Realmwalker(final Realmwalker card) {
        super(card);
    }

    @Override
    public Realmwalker copy() {
        return new Realmwalker(this);
    }
}
