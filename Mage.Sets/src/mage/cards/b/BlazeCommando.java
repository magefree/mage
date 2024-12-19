package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SpellControlledDealsDamageTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierTokenWithHaste;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlazeCommando extends CardImpl {

    public BlazeCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.subtype.add(SubType.MINOTAUR, SubType.SOLDIER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever an instant or sorcery spell you control deals damage, create two 1/1 red and white Soldier creature tokens with haste.
        this.addAbility(new SpellControlledDealsDamageTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new SoldierTokenWithHaste(), 2),
                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, false
        ));

    }

    private BlazeCommando(final BlazeCommando card) {
        super(card);
    }

    @Override
    public BlazeCommando copy() {
        return new BlazeCommando(this);
    }

}
