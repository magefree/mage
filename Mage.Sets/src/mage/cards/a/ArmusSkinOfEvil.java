package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ArmusSkinOfEvil extends CardImpl {

    public ArmusSkinOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of your end step, target opponent discards two cards.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {2}{B}, Discard this card: Target creature gets -4/-4 until end of turn.
        ability = new SimpleActivatedAbility(Zone.HAND, new BoostTargetEffect(-4, -4), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArmusSkinOfEvil(final ArmusSkinOfEvil card) {
        super(card);
    }

    @Override
    public ArmusSkinOfEvil copy() {
        return new ArmusSkinOfEvil(this);
    }
}
