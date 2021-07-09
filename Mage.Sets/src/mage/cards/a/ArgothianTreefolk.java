package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class ArgothianTreefolk extends CardImpl {

    public ArgothianTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Prevent all damage that would be dealt to Argothian Treefolk by artifact sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect(CardType.ARTIFACT)));
    }

    private ArgothianTreefolk(final ArgothianTreefolk card) {
        super(card);
    }

    @Override
    public ArgothianTreefolk copy() {
        return new ArgothianTreefolk(this);
    }
}

class PreventDamageToSourceByCardTypeEffect extends PreventAllDamageToSourceEffect {

    private CardType cardType;

    public PreventDamageToSourceByCardTypeEffect() {
        this(null);
    }

    public PreventDamageToSourceByCardTypeEffect(CardType cardT) {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to {this} by artifact sources";
        cardType = cardT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.getCardType(game).contains(cardType)) {
                return event.getTargetId().equals(source.getSourceId());
            }
        }
        return false;
    }
}