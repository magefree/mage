package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ShamanOfThePack extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("equal to the number of elves you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.ELF.getPredicate());
    }

    public ShamanOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Shaman of the Pack enters the battlefield, target opponent loses life equal to the number of Elves you control.
        Effect effect = new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("target opponent loses life equal to the number of Elves you control");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponent());
        ability.addHint(new ValueHint("Elves you control", new PermanentsOnBattlefieldCount(filter)));
        this.addAbility(ability);
    }

    private ShamanOfThePack(final ShamanOfThePack card) {
        super(card);
    }

    @Override
    public ShamanOfThePack copy() {
        return new ShamanOfThePack(this);
    }
}
