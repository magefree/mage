package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RankleMasterOfPranks extends CardImpl {

    public RankleMasterOfPranks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Rankle, Master of Pranks deals combat damage to a player, choose any number —
        // • Each player discards a card.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardEachPlayerEffect(), false);

        // • Each player loses 1 life and draws a card.
        Mode mode = new Mode(new LoseLifeAllPlayersEffect(1));
        mode.addEffect(new DrawCardAllEffect(1).setText("and draws a card"));
        ability.addMode(mode);

        // • Each player sacrifices a creature.
        ability.addMode(new Mode(new SacrificeAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(3);
        ability.getModes().setChooseText("choose any number &mdash;");
        this.addAbility(ability);
    }

    private RankleMasterOfPranks(final RankleMasterOfPranks card) {
        super(card);
    }

    @Override
    public RankleMasterOfPranks copy() {
        return new RankleMasterOfPranks(this);
    }
}
