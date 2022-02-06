package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TiminYouthfulGeist extends CardImpl {

    public TiminYouthfulGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Partner with Rhoda, Geist Avenger
        this.addAbility(new PartnerWithAbility("Rhoda, Geist Avenger"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each combat, tap up to one target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new TapTargetEffect().setText("tap up to one target creature"),
                TargetController.ANY, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private TiminYouthfulGeist(final TiminYouthfulGeist card) {
        super(card);
    }

    @Override
    public TiminYouthfulGeist copy() {
        return new TiminYouthfulGeist(this);
    }
}
