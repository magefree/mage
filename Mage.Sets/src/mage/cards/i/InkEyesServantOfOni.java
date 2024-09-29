package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class InkEyesServantOfOni extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card from that player's graveyard");

    public InkEyesServantOfOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {3}{B}{B} ({3}{B}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{3}{B}{B}"));

        // Whenever Ink-Eyes, Servant of Oni deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true, true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);

        // {1}{B}: Regenerate Ink-Eyes.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private InkEyesServantOfOni(final InkEyesServantOfOni card) {
        super(card);
    }

    @Override
    public InkEyesServantOfOni copy() {
        return new InkEyesServantOfOni(this);
    }
}
