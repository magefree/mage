package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DragonToken;
import mage.game.permanent.token.KherKeepKoboldToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RohgahhKherKeepOverlord extends CardImpl {

    private static final FilterCreaturePermanent controlledKoboldsFilter = new FilterCreaturePermanent(SubType.KOBOLD, "Kobolds");
    private static final FilterSpell koboldSpellFilter = new FilterSpell("a Kobold spell");
    private static final FilterSpell dragonSpellFilter = new FilterSpell("a Dragon spell");

    static {
        koboldSpellFilter.add(SubType.KOBOLD.getPredicate());
        dragonSpellFilter.add(SubType.DRAGON.getPredicate());
    }

    public RohgahhKherKeepOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOBOLD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other Kobolds you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, controlledKoboldsFilter, true
        )));

        // Whenever you cast a Kobold spell, you may pay {2}. If you do, create a 4/4 red Dragon creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new CreateTokenEffect(new DragonToken()),
                        new ManaCostsImpl<>("{2}")
                ),
                koboldSpellFilter,
                false
        ));

        // Whenever you cast a Dragon spell, create a 0/1 red Kobold creature token named Kobolds of Kher Keep.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new KherKeepKoboldToken()),
                dragonSpellFilter,
                false
        ));
    }

    private RohgahhKherKeepOverlord(final RohgahhKherKeepOverlord card) {
        super(card);
    }

    @Override
    public RohgahhKherKeepOverlord copy() {
        return new RohgahhKherKeepOverlord(this);
    }
}
