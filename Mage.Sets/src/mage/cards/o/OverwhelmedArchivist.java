package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverwhelmedArchivist extends CardImpl {

    public OverwhelmedArchivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.a.ArchiveHaunt.class;

        // When Overwhelmed Archivist enters the battlefield, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Disturb {3}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{3}{U}")));
    }

    private OverwhelmedArchivist(final OverwhelmedArchivist card) {
        super(card);
    }

    @Override
    public OverwhelmedArchivist copy() {
        return new OverwhelmedArchivist(this);
    }
}
