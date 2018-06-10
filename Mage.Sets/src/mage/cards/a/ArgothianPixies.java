
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author MarcoMarin
 */
public final class ArgothianPixies extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public ArgothianPixies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Argothian Pixies can't be blocked by artifact creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect2(CardType.ARTIFACT)));
        // Prevent all damage that would be dealt to Argothian Pixies by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    public ArgothianPixies(final ArgothianPixies card) {
        super(card);
    }

    @Override
    public ArgothianPixies copy() {
        return new ArgothianPixies(this);
    }
}

class PreventDamageToSourceByCardTypeEffect2 extends PreventAllDamageToSourceEffect {

    private CardType cardType;

    public PreventDamageToSourceByCardTypeEffect2() {
        this(null);
    }

    public PreventDamageToSourceByCardTypeEffect2(CardType cardT) {
        super(Duration.WhileOnBattlefield);
        cardType = cardT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (game.getObject(event.getSourceId()).getCardType().contains(cardType)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
