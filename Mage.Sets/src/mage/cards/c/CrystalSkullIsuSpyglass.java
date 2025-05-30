package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterHistoricCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystalSkullIsuSpyglass extends CardImpl {

    private static final FilterCard filter = new FilterHistoricCard("play historic lands and cast historic spells");

    public CrystalSkullIsuSpyglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play historic lands and cast historic spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private CrystalSkullIsuSpyglass(final CrystalSkullIsuSpyglass card) {
        super(card);
    }

    @Override
    public CrystalSkullIsuSpyglass copy() {
        return new CrystalSkullIsuSpyglass(this);
    }
}
