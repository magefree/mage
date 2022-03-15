package mage.cards.o;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.BlueBirdToken;

/**
 *
 * @author LoneFox
 *
 */
public final class OrderedMigration extends CardImpl {

    public OrderedMigration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}");

        // Domain - Create a 1/1 blue Bird creature token with flying for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlueBirdToken(), DomainValue.REGULAR));
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private OrderedMigration(final OrderedMigration card) {
        super(card);
    }

    @Override
    public OrderedMigration copy() {
        return new OrderedMigration(this);
    }
}

// TODO: There is a player rewards token for this (http://magiccards.info/extra/token/player-rewards-2001/bird.html),
// but player rewards tokens are not downloaded...
