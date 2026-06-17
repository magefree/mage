package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheImmortalWeapons extends CardImpl {

    public TheImmortalWeapons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When The Immortal Weapons enter, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, target creature gets +2/+0 and gains menace until end of turn.
        Ability ability2 = new SpellCastControllerTriggeredAbility(
            new BoostTargetEffect(2, 0).setText("target creature gets +2/+0"),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability2.addEffect(new GainAbilityTargetEffect(new MenaceAbility()).setText("and gains menace until end of turn"));
        ability2.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability2);
    }

    private TheImmortalWeapons(final TheImmortalWeapons card) {
        super(card);
    }

    @Override
    public TheImmortalWeapons copy() {
        return new TheImmortalWeapons(this);
    }
}
