package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainActivatedAbilitiesOfTopCardEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class ConspicuousSnoop extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast Goblin spells");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public ConspicuousSnoop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect()));

        // You may cast Goblin spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // As long as the top card of your library is a Goblin card, Conspicuous Snoop has all activated abilities of that card.
        this.addAbility(new SimpleStaticAbility(new GainActivatedAbilitiesOfTopCardEffect(filter.copy().withMessage("a Goblin card"))));
    }

    private ConspicuousSnoop(final ConspicuousSnoop card) {
        super(card);
    }

    @Override
    public ConspicuousSnoop copy() {
        return new ConspicuousSnoop(this);
    }
}
