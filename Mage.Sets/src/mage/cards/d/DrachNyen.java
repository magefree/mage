package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrachNyen extends CardImpl {

    public DrachNyen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Echo of the First Murder -- When Drach'Nyen enters the battlefield exile up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Echo of the First Murder"));

        // Daemon Sword -- Equipped creature has menace and gets +X/+0, where X is the exiled card's power.
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new MenaceAbility(false), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new BoostEquippedEffect(
                DrachNyenValue.instance, StaticValue.get(0)
        ).setText("and gets +X/+0, where X is the exiled card's power"));
        this.addAbility(ability.withFlavorWord("Daemon Sword"));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private DrachNyen(final DrachNyen card) {
        super(card);
    }

    @Override
    public DrachNyen copy() {
        return new DrachNyen(this);
    }
}

enum DrachNyenValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, sourceAbility));
        if (exileZone == null || exileZone.isEmpty()) {
            return 0;
        }
        return exileZone
                .getCards(game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public DrachNyenValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
