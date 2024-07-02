package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.FaerieRogueToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class AlelaCunningConqueror extends CardImpl {

    private static final FilterCreaturePermanent faerieFilter = new FilterCreaturePermanent(SubType.FAERIE, "Faeries");
    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public AlelaCunningConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast your first spell during each opponent's turn, create a 1/1 black Faerie Rogue creature token with flying.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new CreateTokenEffect(new FaerieRogueToken()), false
        ));

        // Whenever one or more Faeries you control deal combat damage to a player, goad target creature that player controls.
        Effect effect = new GoadTargetEffect().setText("goad target creature that player controls");
        Ability ability = new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD, effect, faerieFilter, SetTargetPointer.PLAYER, false);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());

        this.addAbility(ability);
    }

    private AlelaCunningConqueror(final AlelaCunningConqueror card) {
        super(card);
    }

    @Override
    public AlelaCunningConqueror copy() {
        return new AlelaCunningConqueror(this);
    }
}
