package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class WrexialTheRisenDeep extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card from that player's graveyard");

    public WrexialTheRisenDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Whenever Wrexial, the Risen Deep deals combat damage to a player, you may cast target instant or sorcery card from that player's graveyard without paying its mana cost.
        // If that card would be put into a graveyard this turn, exile it instead.
        OneShotEffect effect = new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true);
        effect.setText("you may cast target instant or sorcery card from "
                + "that player's graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private WrexialTheRisenDeep(final WrexialTheRisenDeep card) {
        super(card);
    }

    @Override
    public WrexialTheRisenDeep copy() {
        return new WrexialTheRisenDeep(this);
    }
}
