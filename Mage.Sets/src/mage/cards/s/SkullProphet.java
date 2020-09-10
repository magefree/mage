package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkullProphet extends CardImpl {

    public SkullProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {T}: Put the top two cards of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(2), new TapSourceCost()
        ));
    }

    private SkullProphet(final SkullProphet card) {
        super(card);
    }

    @Override
    public SkullProphet copy() {
        return new SkullProphet(this);
    }
}
