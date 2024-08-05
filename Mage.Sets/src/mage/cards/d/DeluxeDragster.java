package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801, Grath, xenohedron, notgreat
 */
public final class DeluxeDragster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Vehicles");
    private static final FilterCard filterCard = new FilterInstantOrSorceryCard("instant or sorcery card from that player's graveyard");

    static {
        filter.add(Predicates.not(SubType.VEHICLE.getPredicate()));
    }

    public DeluxeDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Deluxe Dragster can’t be blocked except by Vehicles.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Deluxe Dragster deals combat damage to a player, you may cast target instant or sorcery card from
        // that player’s graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        OneShotEffect effect = new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true);
        effect.setText("you may cast target instant or sorcery card from "
                + "that player's graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCardInGraveyard(filterCard));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DeluxeDragster(final DeluxeDragster card) {
        super(card);
    }

    @Override
    public DeluxeDragster copy() {
        return new DeluxeDragster(this);
    }
}
