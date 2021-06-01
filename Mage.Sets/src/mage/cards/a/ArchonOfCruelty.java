package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfCruelty extends CardImpl {

    public ArchonOfCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Archon of Cruelty enters the battlefield or attacks, target opponent sacrifices a creature or planeswalker, discards a card, and loses 3 life. You draw a card and gain 3 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A, 1, "target opponent"
        ));
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new DiscardTargetEffect(1, false).setText(", discards a card"));
        ability.addEffect(new LoseLifeTargetEffect(3).setText(", and loses 3 life."));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("You"));
        ability.addEffect(new GainLifeEffect(3).setText("and gain 3 life"));
        this.addAbility(ability);
    }

    private ArchonOfCruelty(final ArchonOfCruelty card) {
        super(card);
    }

    @Override
    public ArchonOfCruelty copy() {
        return new ArchonOfCruelty(this);
    }
}
