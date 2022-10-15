package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CourserOfKruphix extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("play lands");

    public CourserOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect()));

        // You may play lands from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // Whenever a land enters the battlefield under your control, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1)));
    }

    private CourserOfKruphix(final CourserOfKruphix card) {
        super(card);
    }

    @Override
    public CourserOfKruphix copy() {
        return new CourserOfKruphix(this);
    }
}
