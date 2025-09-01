package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Grath
 */
public final class RevTitheExtractor extends CardImpl {

    public RevTitheExtractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack, target creature gains deathtouch until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more creatures you control deal combat damage to a player, create a Treasure token, then look at the top card of that player's library and exile it face down. You may cast that card for as long as it remains exiled.
        ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new TreasureToken()), StaticFilters.FILTER_PERMANENT_CREATURES, SetTargetPointer.PLAYER, false
        );
        ability.addEffect(new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(true, CastManaAdjustment.NONE)
                        .setText(", then look at the top card of that player's library and exile it face down. "
                                + "You may cast that card for as long as it remains exiled"));
        this.addAbility(ability);
    }

    private RevTitheExtractor(final RevTitheExtractor card) {
        super(card);
    }

    @Override
    public RevTitheExtractor copy() {
        return new RevTitheExtractor(this);
    }
}
