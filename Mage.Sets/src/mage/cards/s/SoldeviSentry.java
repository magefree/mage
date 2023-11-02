package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.RegenerateSourceWithReflexiveEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Ketsuban
 */
public final class SoldeviSentry extends CardImpl {

    public SoldeviSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Choose target opponent. Regenerate Soldevi Sentry. When it regenerates this way, that player may draw a card.
        Ability ability = new SimpleActivatedAbility(
                new RegenerateSourceWithReflexiveEffect(new ReflexiveTriggeredAbility(
                        new DrawCardTargetEffect(1, true),
                        false
                ), true)
                        .setText("Choose target opponent. Regenerate Soldevi Sentry. "
                                + "When it regenerates this way, that player may draw a card"),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SoldeviSentry(final SoldeviSentry card) {
        super(card);
    }

    @Override
    public SoldeviSentry copy() {
        return new SoldeviSentry(this);
    }
}